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
import { ITourExtraCategory } from 'app/shared/model/tour-extra-category.model';
import { getEntities as getTourExtraCategories } from 'app/entities/tour-extra-category/tour-extra-category.reducer';
import { ITour } from 'app/shared/model/tour.model';
import { getEntities as getTours } from 'app/entities/tour/tour.reducer';
import { ITourExtra } from 'app/shared/model/tour-extra.model';
import { getEntity, updateEntity, createEntity, reset } from './tour-extra.reducer';

export const TourExtraUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const users = useAppSelector(state => state.userManagement.users);
  const tags = useAppSelector(state => state.tag.entities);
  const tourExtraCategories = useAppSelector(state => state.tourExtraCategory.entities);
  const tours = useAppSelector(state => state.tour.entities);
  const tourExtraEntity = useAppSelector(state => state.tourExtra.entity);
  const loading = useAppSelector(state => state.tourExtra.loading);
  const updating = useAppSelector(state => state.tourExtra.updating);
  const updateSuccess = useAppSelector(state => state.tourExtra.updateSuccess);

  const handleClose = () => {
    navigate('/tour-extra' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getUsers({}));
    dispatch(getTags({}));
    dispatch(getTourExtraCategories({}));
    dispatch(getTours({}));
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
    if (values.price !== undefined && typeof values.price !== 'number') {
      values.price = Number(values.price);
    }
    if (values.offer !== undefined && typeof values.offer !== 'number') {
      values.offer = Number(values.offer);
    }

    const entity = {
      ...tourExtraEntity,
      ...values,
      createdBy: users.find(it => it.id.toString() === values.createdBy?.toString()),
      tags: mapIdList(values.tags),
      categories: mapIdList(values.categories),
      tours: mapIdList(values.tours),
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
          ...tourExtraEntity,
          createdBy: tourExtraEntity?.createdBy?.id,
          tags: tourExtraEntity?.tags?.map(e => e.id.toString()),
          categories: tourExtraEntity?.categories?.map(e => e.id.toString()),
          tours: tourExtraEntity?.tours?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="xploraAdminApp.tourExtra.home.createOrEditLabel" data-cy="TourExtraCreateUpdateHeading">
            <Translate contentKey="xploraAdminApp.tourExtra.home.createOrEditLabel">Create or edit a TourExtra</Translate>
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
                  id="tour-extra-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('xploraAdminApp.tourExtra.code')}
                id="tour-extra-code"
                name="code"
                data-cy="code"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('xploraAdminApp.tourExtra.enabled')}
                id="tour-extra-enabled"
                name="enabled"
                data-cy="enabled"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('xploraAdminApp.tourExtra.icon')}
                id="tour-extra-icon"
                name="icon"
                data-cy="icon"
                type="text"
              />
              <ValidatedField
                label={translate('xploraAdminApp.tourExtra.price')}
                id="tour-extra-price"
                name="price"
                data-cy="price"
                type="text"
              />
              <ValidatedField
                label={translate('xploraAdminApp.tourExtra.offer')}
                id="tour-extra-offer"
                name="offer"
                data-cy="offer"
                type="text"
              />
              <ValidatedField
                label={translate('xploraAdminApp.tourExtra.shopProductId')}
                id="tour-extra-shopProductId"
                name="shopProductId"
                data-cy="shopProductId"
                type="text"
              />
              <ValidatedField
                label={translate('xploraAdminApp.tourExtra.shopUrl')}
                id="tour-extra-shopUrl"
                name="shopUrl"
                data-cy="shopUrl"
                type="text"
              />
              <ValidatedField
                label={translate('xploraAdminApp.tourExtra.createdDate')}
                id="tour-extra-createdDate"
                name="createdDate"
                data-cy="createdDate"
                type="date"
              />
              <ValidatedField
                label={translate('xploraAdminApp.tourExtra.defaultImage')}
                id="tour-extra-defaultImage"
                name="defaultImage"
                data-cy="defaultImage"
                type="text"
              />
              <ValidatedBlobField
                label={translate('xploraAdminApp.tourExtra.defaultImageData')}
                id="tour-extra-defaultImageData"
                name="defaultImageData"
                data-cy="defaultImageData"
                isImage
                accept="image/*"
              />
              <ValidatedField
                id="tour-extra-createdBy"
                name="createdBy"
                data-cy="createdBy"
                label={translate('xploraAdminApp.tourExtra.createdBy')}
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
                label={translate('xploraAdminApp.tourExtra.tags')}
                id="tour-extra-tags"
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
                label={translate('xploraAdminApp.tourExtra.category')}
                id="tour-extra-category"
                data-cy="category"
                type="select"
                multiple
                name="categories"
              >
                <option value="" key="0" />
                {tourExtraCategories
                  ? tourExtraCategories.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.code}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label={translate('xploraAdminApp.tourExtra.tours')}
                id="tour-extra-tours"
                data-cy="tours"
                type="select"
                multiple
                name="tours"
              >
                <option value="" key="0" />
                {tours
                  ? tours.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.code}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/tour-extra" replace color="info">
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

export default TourExtraUpdate;
