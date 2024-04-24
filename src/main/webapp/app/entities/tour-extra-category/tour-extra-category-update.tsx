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
import { ITourExtra } from 'app/shared/model/tour-extra.model';
import { getEntities as getTourExtras } from 'app/entities/tour-extra/tour-extra.reducer';
import { ITourExtraCategory } from 'app/shared/model/tour-extra-category.model';
import { getEntity, updateEntity, createEntity, reset } from './tour-extra-category.reducer';

export const TourExtraCategoryUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const users = useAppSelector(state => state.userManagement.users);
  const tourExtras = useAppSelector(state => state.tourExtra.entities);
  const tourExtraCategoryEntity = useAppSelector(state => state.tourExtraCategory.entity);
  const loading = useAppSelector(state => state.tourExtraCategory.loading);
  const updating = useAppSelector(state => state.tourExtraCategory.updating);
  const updateSuccess = useAppSelector(state => state.tourExtraCategory.updateSuccess);

  const handleClose = () => {
    navigate('/tour-extra-category' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getUsers({}));
    dispatch(getTourExtras({}));
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
      ...tourExtraCategoryEntity,
      ...values,
      createdBy: users.find(it => it.id.toString() === values.createdBy?.toString()),
      extras: mapIdList(values.extras),
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
          ...tourExtraCategoryEntity,
          createdBy: tourExtraCategoryEntity?.createdBy?.id,
          extras: tourExtraCategoryEntity?.extras?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="xploraAdminApp.tourExtraCategory.home.createOrEditLabel" data-cy="TourExtraCategoryCreateUpdateHeading">
            <Translate contentKey="xploraAdminApp.tourExtraCategory.home.createOrEditLabel">Create or edit a TourExtraCategory</Translate>
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
                  id="tour-extra-category-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('xploraAdminApp.tourExtraCategory.code')}
                id="tour-extra-category-code"
                name="code"
                data-cy="code"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('xploraAdminApp.tourExtraCategory.enabled')}
                id="tour-extra-category-enabled"
                name="enabled"
                data-cy="enabled"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('xploraAdminApp.tourExtraCategory.icon')}
                id="tour-extra-category-icon"
                name="icon"
                data-cy="icon"
                type="text"
              />
              <ValidatedField
                label={translate('xploraAdminApp.tourExtraCategory.defaultImage')}
                id="tour-extra-category-defaultImage"
                name="defaultImage"
                data-cy="defaultImage"
                type="text"
              />
              <ValidatedBlobField
                label={translate('xploraAdminApp.tourExtraCategory.defaultImageData')}
                id="tour-extra-category-defaultImageData"
                name="defaultImageData"
                data-cy="defaultImageData"
                isImage
                accept="image/*"
              />
              <ValidatedField
                label={translate('xploraAdminApp.tourExtraCategory.shopCategoryId')}
                id="tour-extra-category-shopCategoryId"
                name="shopCategoryId"
                data-cy="shopCategoryId"
                type="text"
              />
              <ValidatedField
                label={translate('xploraAdminApp.tourExtraCategory.shopUrl')}
                id="tour-extra-category-shopUrl"
                name="shopUrl"
                data-cy="shopUrl"
                type="text"
              />
              <ValidatedField
                label={translate('xploraAdminApp.tourExtraCategory.createdDate')}
                id="tour-extra-category-createdDate"
                name="createdDate"
                data-cy="createdDate"
                type="date"
              />
              <ValidatedField
                id="tour-extra-category-createdBy"
                name="createdBy"
                data-cy="createdBy"
                label={translate('xploraAdminApp.tourExtraCategory.createdBy')}
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
                label={translate('xploraAdminApp.tourExtraCategory.extra')}
                id="tour-extra-category-extra"
                data-cy="extra"
                type="select"
                multiple
                name="extras"
              >
                <option value="" key="0" />
                {tourExtras
                  ? tourExtras.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.code}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/tour-extra-category" replace color="info">
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

export default TourExtraCategoryUpdate;
