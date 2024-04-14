import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './language.reducer';

export const LanguageDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const languageEntity = useAppSelector(state => state.language.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="languageDetailsHeading">
          <Translate contentKey="xploraAdminApp.language.detail.title">Language</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{languageEntity.id}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="xploraAdminApp.language.code">Code</Translate>
            </span>
          </dt>
          <dd>{languageEntity.code}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="xploraAdminApp.language.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {languageEntity.createdDate ? (
              <TextFormat value={languageEntity.createdDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="icon">
              <Translate contentKey="xploraAdminApp.language.icon">Icon</Translate>
            </span>
          </dt>
          <dd>{languageEntity.icon}</dd>
          <dt>
            <span id="defaultImage">
              <Translate contentKey="xploraAdminApp.language.defaultImage">Default Image</Translate>
            </span>
          </dt>
          <dd>{languageEntity.defaultImage}</dd>
          <dt>
            <span id="defaultImageData">
              <Translate contentKey="xploraAdminApp.language.defaultImageData">Default Image Data</Translate>
            </span>
          </dt>
          <dd>
            {languageEntity.defaultImageData ? (
              <div>
                {languageEntity.defaultImageDataContentType ? (
                  <a onClick={openFile(languageEntity.defaultImageDataContentType, languageEntity.defaultImageData)}>
                    <img
                      src={`data:${languageEntity.defaultImageDataContentType};base64,${languageEntity.defaultImageData}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                ) : null}
                <span>
                  {languageEntity.defaultImageDataContentType}, {byteSize(languageEntity.defaultImageData)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="xploraAdminApp.language.createdBy">Created By</Translate>
          </dt>
          <dd>{languageEntity.createdBy ? languageEntity.createdBy.login : ''}</dd>
        </dl>
        <Button tag={Link} to="/language" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/language/${languageEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default LanguageDetail;
