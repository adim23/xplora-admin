import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { ITag } from 'app/shared/model/tag.model';
import { getEntities as getTags } from 'app/entities/tag/tag.reducer';
import { IPlaceCategory } from 'app/shared/model/place-category.model';
import { getEntities as getPlaceCategories } from 'app/entities/place-category/place-category.reducer';
import { IDestination } from 'app/shared/model/destination.model';
import { getEntities as getDestinations } from 'app/entities/destination/destination.reducer';
import { IPlace } from 'app/shared/model/place.model';
import { getEntity, updateEntity, createEntity, reset } from './place.reducer';

export const PlaceUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const users = useAppSelector(state => state.userManagement.users);
  const tags = useAppSelector(state => state.tag.entities);
  const placeCategories = useAppSelector(state => state.placeCategory.entities);
  const destinations = useAppSelector(state => state.destination.entities);
  const placeEntity = useAppSelector(state => state.place.entity);
  const loading = useAppSelector(state => state.place.loading);
  const updating = useAppSelector(state => state.place.updating);
  const updateSuccess = useAppSelector(state => state.place.updateSuccess);

  const handleClose = () => {
    navigate('/place' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getUsers({}));
    dispatch(getTags({}));
    dispatch(getPlaceCategories({}));
    dispatch(getDestinations({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }

    const entity = {
      ...placeEntity,
      ...values,
      createdBy: users.find(it => it.id.toString() === values.createdBy?.toString()),
      tags: mapIdList(values.tags),
      categories: mapIdList(values.categories),
      destination: destinations.find(it => it.id.toString() === values.destination?.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...placeEntity,
          createdBy: placeEntity?.createdBy?.id,
          tags: placeEntity?.tags?.map(e => e.id.toString()),
          categories: placeEntity?.categories?.map(e => e.id.toString()),
          destination: placeEntity?.destination?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="xploraAdminApp.place.home.createOrEditLabel" data-cy="PlaceCreateUpdateHeading">
            <Translate contentKey="xploraAdminApp.place.home.createOrEditLabel">Create or edit a Place</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="place-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('xploraAdminApp.place.code')}
                id="place-code"
                name="code"
                data-cy="code"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('xploraAdminApp.place.enabled')}
                id="place-enabled"
                name="enabled"
                data-cy="enabled"
                check
                type="checkbox"
              />
              <ValidatedField label={translate('xploraAdminApp.place.icon')} id="place-icon" name="icon" data-cy="icon" type="text" />
              <ValidatedField
                label={translate('xploraAdminApp.place.destinationSight')}
                id="place-destinationSight"
                name="destinationSight"
                data-cy="destinationSight"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('xploraAdminApp.place.longitude')}
                id="place-longitude"
                name="longitude"
                data-cy="longitude"
                type="text"
              />
              <ValidatedField
                label={translate('xploraAdminApp.place.latitude')}
                id="place-latitude"
                name="latitude"
                data-cy="latitude"
                type="text"
              />
              <ValidatedField
                label={translate('xploraAdminApp.place.createdDate')}
                id="place-createdDate"
                name="createdDate"
                data-cy="createdDate"
                type="date"
              />
              <ValidatedField
                label={translate('xploraAdminApp.place.defaultImage')}
                id="place-defaultImage"
                name="defaultImage"
                data-cy="defaultImage"
                type="text"
              />
              <ValidatedBlobField
                label={translate('xploraAdminApp.place.defaultImageData')}
                id="place-defaultImageData"
                name="defaultImageData"
                data-cy="defaultImageData"
                isImage
                accept="image/*"
              />
              <ValidatedField
                id="place-createdBy"
                name="createdBy"
                data-cy="createdBy"
                label={translate('xploraAdminApp.place.createdBy')}
                type="select"
              >
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.login}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label={translate('xploraAdminApp.place.tags')}
                id="place-tags"
                data-cy="tags"
                type="select"
                multiple
                name="tags"
              >
                <option value="" key="0" />
                {tags
                  ? tags.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.code}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label={translate('xploraAdminApp.place.category')}
                id="place-category"
                data-cy="category"
                type="select"
                multiple
                name="categories"
              >
                <option value="" key="0" />
                {placeCategories
                  ? placeCategories.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.code}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="place-destination"
                name="destination"
                data-cy="destination"
                label={translate('xploraAdminApp.place.destination')}
                type="select"
              >
                <option value="" key="0" />
                {destinations
                  ? destinations.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.code}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/place" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default PlaceUpdate;
